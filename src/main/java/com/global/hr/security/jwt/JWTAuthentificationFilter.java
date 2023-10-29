package com.global.hr.security.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.global.hr.security.LibraryUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

//token kijena njarbu  fih maa l admin makhdemch w aamali unauthorized spring mayaarech
//chkoun l owner mte3u yelzem ysir filtrage
//we should decode this secret key
@Component
@RequiredArgsConstructor
public class JWTAuthentificationFilter extends OncePerRequestFilter {
private final JWTService jwtService;
private final LibraryUserDetailsService userDetailsService;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//we get the header li hia when lauthorization w nji chnekteb token
		 String authHeader = request.getHeader("Authorization");
	        String token = null;
	        String userName = null;
	        if (authHeader != null && authHeader.startsWith("Bearer ")){
	        	//extract les lettres li baad Bearer
	            token = authHeader.substring(7);
	            userName = jwtService.extractUsernameFromToken(token);
	        }
	        //test si username exist w mazel mch authentificated
	        if (userName != null & SecurityContextHolder.getContext().getAuthentication() == null) {
	        	//nverifi est ce que mwjud fl base wella
	            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
	            //username valid maa userdetails wella
	            if(jwtService.validateToken(token, userDetails)) {
	                var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                SecurityContextHolder.getContext().setAuthentication(authToken);
	            }
	        }
	        filterChain.doFilter(request, response);
		
		
	}

}
