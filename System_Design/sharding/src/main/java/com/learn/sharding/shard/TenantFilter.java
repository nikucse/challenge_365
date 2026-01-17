package com.learn.sharding.shard;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * DEMO-ONLY FILTER.
 *
 * In production:
 * - schoolCode comes from JWT
 *
 * In demo:
 * - schoolCode comes from HTTP header: X-School-Code
 *
 * This filter MUST run before any business logic.
 */
@Component
public class TenantFilter extends OncePerRequestFilter {

    private static final String HEADER = "X-School-Code";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        if (path.startsWith("/schools")) {
            filterChain.doFilter(request, response);
            return;
        }

        String schoolCode = request.getHeader(HEADER);

        if (schoolCode == null || schoolCode.isBlank()) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Missing required header: X-School-Code"
            );
            return;
        }

        // Store tenant identity for this request
        ShardContext.setSchoolCode(schoolCode);

        try {
            filterChain.doFilter(request, response);
        } finally {
            // VERY IMPORTANT: avoid ThreadLocal leaks
            ShardContext.clear();
        }
    }
}
