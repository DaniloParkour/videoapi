package com.piter.videoapi.exceptions;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class TokenUserNotIdentified extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	@NonNull
	private final String message;

}
