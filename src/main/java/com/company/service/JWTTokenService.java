package com.company.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.dto.TokenDTO;
import com.company.entity.User;
import com.company.entity.Token;
import com.company.entity.Token.Type;
import com.company.repository.TokenRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JWTTokenService implements IJWTTokenService{
	
	@Value("${jwt.token.time.expiration}")
    private long EXPIRATION_TIME;
	
	@Value("${jwt.token.secret}")
	private String SECRET;
	
	@Value("${jwt.token.header.authorization}")
	private String TOKEN_AUTHORIZATION;
	
	@Value("${jwt.token.prefix}")
	private String TOKEN_PREFIX;
	
	@Value("${jwt.refreshtoken.time.expiration}")
	private long REFRESH_EXPIRATION_TIME;
	
	@Autowired
	private UserService accountService;
	
	@Autowired
	private TokenRepository tokenRepository;

    @Override
    public String generateJWTToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }
    
    public Authentication parseTokenToUserInformation(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_AUTHORIZATION);
        
        if (token == null) {
        	return null;
        }
        
        // parse the token
        try {
        String username = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();
        
        User account = accountService.getAccountByUsername(username);

        return username != null ?
                new UsernamePasswordAuthenticationToken(
                		account.getUsername(), 
                		null, 
                		AuthorityUtils.createAuthorityList(account.getRole().toString())) :
                null;
        } catch (Exception e) {
			return null;
		}
    }

	@Override
	@Transactional
	public Token generateRefreshToken(User account) {
		Token refreshToken = new Token(
				account, 
				UUID.randomUUID().toString(), 
				Token.Type.REFRESH_TOKEN,
				new Date(new Date().getTime() + REFRESH_EXPIRATION_TIME));
		
		// delete all old refresh token of this account
		tokenRepository.deleteByAccount(account);

		// create new token
		return tokenRepository.save(refreshToken);
	}
	
	@Override
	public boolean isRefreshTokenValid(String refreshToken) {
		Token entity = tokenRepository.findBykeyAndType(refreshToken, Type.REFRESH_TOKEN);
		if (entity == null || entity.getExpiredDate().before(new Date())) {
			return false;
		}
		return true;
	}

	
	@Override
	@Transactional
	public TokenDTO getNewToken(String refreshToken) {
		// find old refresh token
		Token oldRefreshToken = tokenRepository.findBykeyAndType(refreshToken, Type.REFRESH_TOKEN);

		// delete old refresh token
		tokenRepository.deleteByAccount(oldRefreshToken.getAccount());

		// create new refresh token
		Token newRefreshToken = generateRefreshToken(oldRefreshToken.getAccount());

		// create new jwt token
		String newToken = generateJWTToken(oldRefreshToken.getAccount().getUsername());

		return new TokenDTO(newToken, newRefreshToken.getKey());
	}
}

