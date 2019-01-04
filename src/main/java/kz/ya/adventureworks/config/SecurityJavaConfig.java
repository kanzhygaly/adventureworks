///**
// * Rest Security Configuration Class
// */
//package kz.ya.adventureworks.config;
//
//import kz.ya.adventureworks.security.CustomAccessDeniedHandler;
//import kz.ya.adventureworks.security.MySavedRequestAwareAuthenticationSuccessHandler;
//import kz.ya.adventureworks.security.RestAuthenticationEntryPoint;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
//
///**
// *
// * @author yerlana
// */
////@Configuration
////@EnableWebSecurity
////@EnableGlobalMethodSecurity(prePostEnabled = true)
////@ComponentScan("kz.ya.adventureworks.security")
//public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private CustomAccessDeniedHandler accessDeniedHandler;
//    @Autowired
//    private RestAuthenticationEntryPoint restAuthEntryPoint;
//    @Autowired
//    private MySavedRequestAwareAuthenticationSuccessHandler successHandler;
//    private final SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();
//
//    public SecurityJavaConfig() {
//        super();
//        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("advUser").password(encoder().encode("advPass")).roles("USER");
//    }
//
//    @Bean
//    public PasswordEncoder encoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeRequests()
//                .and()
//                .exceptionHandling()
//                .accessDeniedHandler(accessDeniedHandler)
//                .authenticationEntryPoint(restAuthEntryPoint)
//                .and()
//                .authorizeRequests()
//                .antMatchers("/api/reviews").authenticated() // any authenticated user
//                .and()
//                .formLogin()
//                .successHandler(successHandler)
//                .failureHandler(failureHandler)
//                .and()
//                .httpBasic()
//                .and()
//                .logout();
//    }
//}
