package com.poly.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;
@Service
public class SessionService {
    @Autowired
    HttpSession session;
    private Map<String, String> sessionData;

    public SessionService() {
        sessionData = new HashMap<>();
    }

    public void set(String name, Object value) {
		session.setAttribute(name, value);
	}
	
	public<T> T get(String name) {
		return (T) session.getAttribute(name);
	}

    public void remove(String key) {
        sessionData.remove(key);
    }
    
}
