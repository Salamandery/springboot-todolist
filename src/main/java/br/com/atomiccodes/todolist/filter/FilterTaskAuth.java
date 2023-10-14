package br.com.atomiccodes.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.atomiccodes.todolist.users.IUserRepository;
import br.com.atomiccodes.todolist.users.UserModel;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        String authorization = request.getHeader("Authorization");
        String passwordEncoded = authorization.substring("Basic".length()).trim();

        byte[] passwordDecoded = Base64.getDecoder().decode(passwordEncoded);
        String password = new String(passwordDecoded);

        String[] credentials = password.split(":");
        String username = credentials[0];
        String userPassword = credentials[1];

        UserModel user = this.userRepository.findByUsername(username);

        if (user == null) {
            response.sendError(HttpStatus.UNAUTHORIZED);
        }

        BCrypt.verifyer().verify(userPassword.toCharArray(), user.getPassword());

        filterChain.doFilter(request, response);
    }
}
