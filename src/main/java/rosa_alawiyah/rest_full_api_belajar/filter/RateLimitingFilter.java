//package rosa_alawiyah.rest_full_api_belajar.filter;
//
//import com.bucket4j.Bandwidth;
//import com.bucket4j.Bucket;
//import com.bucket4j.Refill;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.time.Duration;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Component
//public class RateLimitingFilter extends HttpFilter {
//
//    // Simpan bucket per IP address, thread-safe
//    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();
//
//    // Buat bucket dengan limit 100 request per menit
//    private Bucket createNewBucket() {
//        Bandwidth limit = Bandwidth.classic(100, Refill.greedy(100, Duration.ofMinutes(1)));
//        return Bucket.builder()
//                .addLimit(limit)
//                .build();
//    }
//
//    @Override
//    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//
//        String ip = request.getRemoteAddr();
//        Bucket bucket = cache.computeIfAbsent(ip, k -> createNewBucket());
//
//        if (bucket.tryConsume(1)) {
//            chain.doFilter(request, response);
//        } else {
//            response.setStatus(429);
//            response.getWriter().write("Too Many Requests - Rate limit exceeded");
//        }
//    }
//}