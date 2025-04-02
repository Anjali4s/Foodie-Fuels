package com.curiouscoders.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.csrf(customizer -> customizer.disable()).
                authorizeHttpRequests(request -> request
                		.requestMatchers("/employees/delete/{id}").hasRole("ADMIN")
                		.requestMatchers("/employees/home","/employees/update","/employees/register").permitAll()
                		.anyRequest().permitAll()).
                		//authenticated()).
                        
               // http.formLogin(Customizer.withDefaults())
                httpBasic(httpSecurity -> {}).
                sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).build();


    }


//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        UserDetails user1 = User
//                .withDefaultPasswordEncoder()
//                .username("kiran")
//                .password("k@123")
//                .roles("USER")
//                .build();
//
//        UserDetails user2 = User
//                .withDefaultPasswordEncoder()
//                .username("harsh")
//                .password("h@123")
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user1, user2);
//    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService);


        return provider;
    }
}

























//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new CustomUserDetailsService();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity, 
//                                                       PasswordEncoder passwordEncoder, 
//                                                       UserDetailsService userDetailsService) throws Exception {
//        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
//                .userDetailsService(userDetailsService)
//                .passwordEncoder(passwordEncoder)
//                .and()
//                .build();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.csrf().disable()
//                .authorizeRequests(auth -> auth
//                        //.requestMatchers("/employee/register", "/employee/delete/**").hasRole("ADMIN") // Admin-only
//                        .requestMatchers("/employee/update").authenticated() // All authenticated employees
//                        .requestMatchers("/employee/find/{id}", "/employee/findAll").authenticated() // View access
//                        .requestMatchers("/employee/home").permitAll() // Public endpoint
//                )
//                .formLogin(form -> form
//                        .permitAll() // Use default login form
//                        .successHandler(new SavedRequestAwareAuthenticationSuccessHandler())
//                        //.defaultSuccessUrl("/employee/**", false) // Redirect after successful login
//                )
//                .logout(logout -> logout
//                        .logoutSuccessUrl("/employee/home") // Redirect after logout
//                        .permitAll()
//                )
//                .sessionManagement()
//                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Ensure session is created only when needed
//                    .invalidSessionUrl("/login?error=sessionExpired"); // Redirect to login page if session is invalid
//
//        return httpSecurity.build();
//    }
//}
//






















//@Configuration
//public class SecurityConfig {
//    
//	@Autowired
//    private EmployeeService service;
//	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		
//		
//		return new CustomUserDetailsService();
//		
//	}
//	
//	@SuppressWarnings("removal")
//	@Bean 
//	public SecurityFilterChain  filterChain(HttpSecurity httpSecurity) throws Exception {
//		httpSecurity.csrf().disable()
//		.authorizeHttpRequests()		
//		.requestMatchers("/employee/**")
//		.hasRole("ADMIN")
//		.requestMatchers("/employee/update")
//		.authenticated()
//		.requestMatchers("/employee/home")
//		.permitAll()
//		.requestMatchers("/employee/find/{id}","/employee/findAll")
//		.permitAll()
//		.and()
//		.formLogin();
//		
//		return httpSecurity.build();
//	}
//}
