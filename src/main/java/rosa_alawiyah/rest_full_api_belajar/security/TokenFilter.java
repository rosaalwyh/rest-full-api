package rosa_alawiyah.rest_full_api_belajar.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class TokenFilter implements Filter {

    @Autowired
    private TokenManager tokenManager;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;

        String authHeader = httpReq.getHeader("Authorization");

        String path = httpReq.getRequestURI();
        if (path.contains("/api/auth/login") || path.contains("/api/users")) {
            chain.doFilter(request, response);
            return;
        }

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            httpResp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResp.getWriter().write("Missing or invalid Authorization header");
            return;
        }

        String token = authHeader.substring(7);
        try {
            String username = tokenManager.validateToken(token);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null, List.of()); // bisa ditambahkan authorities kalau perlu
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);
        } catch (Exception e) {
            httpResp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResp.getWriter().write("Invalid or expired token: " + e.getMessage());
        }
    }
}
