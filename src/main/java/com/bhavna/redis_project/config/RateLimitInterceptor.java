package com.bhavna.redis_project.config;
import com.bhavna.redis_project.service.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class RateLimitInterceptor implements HandlerInterceptor {

    private final RateLimiterService rateLimiterService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1. Identify Client (IP Address or Header)
        String clientId = request.getHeader("X-Client-Id");
        if (clientId == null || clientId.isEmpty()) {
            clientId = request.getRemoteAddr();
        }

        // 2. Check Rate Limit
        if (!rateLimiterService.isAllowed(clientId)) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Too many requests. Please try again later.\"}");
            return false; // Block the request
        }

        return true; // Allow the request to proceed to Controller
    }
}