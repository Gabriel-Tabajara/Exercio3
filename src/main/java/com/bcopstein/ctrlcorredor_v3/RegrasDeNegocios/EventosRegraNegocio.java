package com.bcopstein.ctrlcorredor_v3.RegrasDeNegocios;

import java.util.List;
import java.util.stream.Collectors;

import com.bcopstein.ctrlcorredor_v3.Objetos.Evento;
import com.bcopstein.ctrlcorredor_v3.Repository.EventoRepository;

public class EventosRegraNegocio {

    public static List<Evento> ListaEventosBaseadosNaDistancia(EventoRepository eventoRep, int distancia) {
        return eventoRep.todos()
            .stream()
            .filter(e->e.getDistancia() == distancia)
            .collect(Collectors.toList());
    }

    public static List<Evento> ListaEventosBaseadosNaDistanciaEAno(EventoRepository eventoRep, int distancia, int ano) {
        return eventoRep.todos()
            .stream()
            .filter(e->e.getAno() == ano)
            .filter(e->e.getDistancia() == distancia)
            .collect(Collectors.toList());
    }

    public static List<Double> ListaTempo(List<Evento> eventos){
        return eventos
        .stream()
        .map(e-> e.getHoras()*60*60 + e.getMinutos()*60.0 + e.getSegundos())
        .sorted()
        .collect(Collectors.toList());
    }

    public static double CalculaMédia(List<Double> valores){
        return valores
        .stream()
        .mapToDouble(v->v)
        .average()
        .orElse(Double.NaN);
    }

    public static double CalculaMediana(List<Double> valores){
        if (valores.size() > 0){
            return ((valores.size() % 2 == 0) ?
                   (valores.get(valores.size()/2 - 1))+(valores.get(valores.size()/2))/2.0 :
                   (valores.get(valores.size()/2)));
        }
        throw new Error("Impossivel calcular mediana com 0 valores.");
    }

    public static double CalculaDesvioPadrao(List<Double> valores, double media){
        double varianca = valores
                .stream()
                .mapToDouble(v -> v - media)
                .map(v -> v*v)
                .average().getAsDouble();
        return Math.sqrt(varianca);
    }

    public static double[] maiorDif(List<Evento> eventos){
        double res[] = new double[2];

        int indiceMaiorDif = 0;
        double maiorDif = -1.0;
        for(int i=0;i<eventos.size()-1;i++){
            Evento e1 = eventos.get(i);
            Evento e2 = eventos.get(i+1);
            double tempo1  = e1.getHoras()*60*60 + e1.getMinutos()*60.0 + e1.getSegundos();
            double tempo2  = e2.getHoras()*60*60 + e2.getMinutos()*60.0 + e2.getSegundos();
            if ((tempo1-tempo2)>maiorDif){
                maiorDif = tempo1-tempo2;
                indiceMaiorDif = i;
            }
        } 
        res[0] = indiceMaiorDif;
        res[1] = maiorDif;

        return res;
    }
}
