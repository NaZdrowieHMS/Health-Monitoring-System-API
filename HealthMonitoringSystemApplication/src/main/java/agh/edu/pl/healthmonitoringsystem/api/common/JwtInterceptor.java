package agh.edu.pl.healthmonitoringsystem.api.common;

import agh.edu.pl.healthmonitoringsystem.domain.model.Role;
import agh.edu.pl.healthmonitoringsystem.domain.service.UserService;
import agh.edu.pl.healthmonitoringsystem.persistence.UserRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

import static agh.edu.pl.healthmonitoringsystem.api.common.Constants.SECRET_KEY;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            return true;
        }

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing or invalid Authorization header");
            return false;
        }

        String token = authorizationHeader.substring(7);

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.get("email").toString();
            Long id = Long.valueOf(claims.get("id").toString());
            Role role = Role.fromString(claims.get("role").toString());
            String password = claims.get("password").toString();

            Optional<UserEntity> userEntity = userRepository.findById(id);
            if (userEntity.isEmpty()) throw new IllegalArgumentException("Invalid token");

            if (!userEntity.get().getPassword().equals(password) || !userEntity.get().getRole().equals(role) || ! userEntity.get().getEmail().equals(email)) {
                throw new IllegalArgumentException("Invalid token");
            }

            if (!request.getHeader("userId").isEmpty()) {
                if (!Long.valueOf(request.getHeader("userId")).equals(id)) throw new IllegalArgumentException("Invalid token");
            }
        } catch (SignatureException | IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or expired token");
            return false;
        }

        return true;
    }
}
