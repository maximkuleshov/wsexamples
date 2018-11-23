package com.tsystems.javaschool.websecurity;

import java.io.IOException;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthServlet extends HttpServlet {

    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    byte[] apiKeySecretBytes = "SECRET".getBytes();
    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

    Map<String, Collection<String>> users = new HashMap<>();
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init(ServletConfig config) throws ServletException {
        users.put("john", Arrays.asList("poweruser"));
        users.put("bill", Arrays.asList("user"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        if (!users.containsKey(username)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        JwtBuilder builder = Jwts.builder().setId(username)
                .setIssuedAt(new Date())
                .setSubject("jwtexample")
                .setIssuer("tsystems")
                .setExpiration(new Date(new Date().getTime() + 3600 * 24))
                .claim("roles", users.get(username))
                .signWith(signatureAlgorithm, signingKey);

        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(builder.compact());

        resp.setContentType("application/json");
        resp.getWriter().print(objectMapper.writeValueAsString(tokenDto));
    }
}
