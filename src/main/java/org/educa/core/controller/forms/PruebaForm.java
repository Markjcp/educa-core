package org.educa.core.controller.forms;

import org.springframework.web.multipart.MultipartFile;

public class PruebaForm {
	
	private MultipartFile video;
	private MultipartFile subtitulo;

	public PruebaForm() {
		super();
	}

	public MultipartFile getVideo() {
		return video;
	}

	public void setVideo(MultipartFile video) {
		this.video = video;
	}

	public MultipartFile getSubtitulo() {
		return subtitulo;
	}

	public void setSubtitulo(MultipartFile subtitulo) {
		this.subtitulo = subtitulo;
	}
}
