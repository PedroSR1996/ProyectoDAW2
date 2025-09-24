package com.cibertec.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "proyecto-reserva")

public interface ReservaFeign {
	
	@GetMapping("/api/reserva/{id}")
	ReservaDTO obReservaDTO (@PathVariable("id") Integer id);

}




/*@FeignClient(name = "servicio-reserva", url = "http://localhost:8080",
	    configuration = FeignClientConfig.class 
	)
	public interface ReservaFeign {

	    @GetMapping("/api/reserva/{id}") 
	    ReservaDTO obReservaDTO(@PathVariable("id") Integer id);
	}
*/