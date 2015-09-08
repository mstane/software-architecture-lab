package org.sm.lab.mybooks.security;

import java.util.ArrayList;
import java.util.List;

import org.sm.lab.mybooks.domain.Reader;
import org.sm.lab.mybooks.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("appUserDetailService")
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
    ReaderRepository readerRepository;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Reader reader = readerRepository.findByUsername(username);

		if (reader == null) {
			throw new UsernameNotFoundException("Wrong username or password");
		}
		
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		GrantedAuthority grantedAuthority = new GrantedAuthorityImpl(reader.getSystemRole().name());
		grantedAuthorities.add(grantedAuthority);
	
		
		UserDetails userDetails = new UserDetailsImpl(reader.getId(), reader.getUsername(), reader.getPassword(), grantedAuthorities, reader.getSystemRole());

		return userDetails;
	}

}
