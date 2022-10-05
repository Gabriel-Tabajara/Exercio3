package com.bcopstein.ctrlcorredor_v3.Endpoints;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bcopstein.ctrlcorredor_v3.Objetos.Corredor;
import com.bcopstein.ctrlcorredor_v3.Objetos.EstatisticasDTO;
import com.bcopstein.ctrlcorredor_v3.Objetos.Evento;
import com.bcopstein.ctrlcorredor_v3.Objetos.PerformanceDTO;
import com.bcopstein.ctrlcorredor_v3.RegrasDeNegocios.EventosRegraNegocio;
import com.bcopstein.ctrlcorredor_v3.Repository.CorredorRepository;
import com.bcopstein.ctrlcorredor_v3.Repository.EventoRepository;

@RestController
@RequestMapping("/ctrlCorridas")
public class CtrlCorridasController {
    private CorredorRepository corredorRep;
    private EventoRepository eventoRep;

    @Autowired
    public CtrlCorridasController(CorredorRepository corredorRep,          
                                  EventoRepository eventoRep) {
        this.corredorRep = corredorRep;
        this.eventoRep = eventoRep;

        System.err.println("\n\ncriado controlador");
    }

    @GetMapping("/corredor")
    @CrossOrigin(origins = "*")
    public List<Corredor> consultaCorredor() {
        return corredorRep.todos();
    }

    @PostMapping("/corredor")
    @CrossOrigin(origins = "*")
    public boolean cadastraCorredor(@RequestBody final Corredor corredor) {
        corredorRep.removeTodos();
        corredorRep.cadastra(corredor);
        return true;
    }

    @GetMapping("/eventos")
    @CrossOrigin(origins = "*")
    public List<Evento> consultaEventos() {
        return eventoRep.todos();
    }

    @PostMapping("/eventos") // adiciona evento no único corredor
    @CrossOrigin(origins = "*")
    public boolean informaEvento(@RequestBody final Evento evento) {
        eventoRep.cadastra(evento);
        return true;
    }

    @GetMapping("/estatisticas")
    @CrossOrigin(origins = "*")
    public EstatisticasDTO estatisticas(@RequestParam final Integer distancia){
        // Seleciona os eventos da distancia informada
        List<Evento> eventos = EventosRegraNegocio.ListaEventosBaseadosNaDistancia(eventoRep, distancia);
        // Obtém um stream com os valores ordenados
        List<Double> valores = EventosRegraNegocio.ListaTempo(eventos);
        // Calcula a média
        double media = EventosRegraNegocio.CalculaMédia(valores);
        // Calcula mediana
        Double mediana = EventosRegraNegocio.CalculaMediana(valores);
        // Calcula o desvio padrao
        double desvioPadrao = EventosRegraNegocio.CalculaDesvioPadrao(valores, media);
        return new EstatisticasDTO(media, mediana, desvioPadrao);
    }

    @GetMapping("/aumentoPerformance")
    @CrossOrigin(origins = "*")
    public PerformanceDTO aumentoPerformance(@RequestParam final Integer distancia,
                                            @RequestParam final Integer ano){
        List<Evento> eventos = EventosRegraNegocio.ListaEventosBaseadosNaDistanciaEAno(eventoRep, distancia, ano);

        double dif[] = EventosRegraNegocio.maiorDif(eventos);
        int indiceMaiorDif = (int) dif[0];
        double maiorDif = dif[1];      
        
        return new PerformanceDTO(eventos.get(indiceMaiorDif).getNome(),
                                  eventos.get(indiceMaiorDif+1).getNome(),
                                  maiorDif);
    }
}
