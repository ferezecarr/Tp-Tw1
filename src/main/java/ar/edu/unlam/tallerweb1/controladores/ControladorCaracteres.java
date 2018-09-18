package ar.edu.unlam.tallerweb1.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorCaracteres {

	@RequestMapping("/{operacion}/{valor}")
	public ModelAndView operacion(@PathVariable String operacion , @PathVariable String valor , String resultado) {
		ModelMap modelo = new ModelMap();
		
		String result = "";
		
		switch(operacion) {
		
			case "pasarAMayuscula":
				result.toUpperCase();
				break;
			
			case "pasarAMinuscula":
				result.toLowerCase();
				break;
			
			case "invertirOrden":
				for(int i = 0 ; i < result.length() ; i++) {
					result.charAt(i);
				}
				break;
			
			case "cantidadDeCaracteres":
				break;
			
			default:
				return new ModelAndView("redirect:/error");
		}
		
		modelo.put("operacion", operacion);
		modelo.put("valor", valor);
		modelo.put("resultado", result);
		
		
		return new ModelAndView("resultado",modelo);
	}
	
	@RequestMapping("/error")
	public ModelAndView operacionDeCaracteresErronea() {
		ModelMap modelo = new ModelMap();
		return new ModelAndView("error",modelo);
	}
}
