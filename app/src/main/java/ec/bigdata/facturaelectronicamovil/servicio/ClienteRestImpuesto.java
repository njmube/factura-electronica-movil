package ec.bigdata.facturaelectronicamovil.servicio;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import ec.bigdata.facturaelectronicamovil.adaptador.AdaptadorImpuesto;
import ec.bigdata.facturaelectronicamovil.modelo.TarifasImpuesto;
import ec.bigdata.facturaelectronicamovil.utilidad.Utilidades;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by DavidLeonardo on 6/6/2016.
 */
public class ClienteRestImpuesto {
    private static ServicioImpuesto servicioImpuesto;

    public static ServicioImpuesto getServicioImpuesto() {
        if (servicioImpuesto == null) {

            Gson gson = new GsonBuilder().registerTypeAdapter(TarifasImpuesto.class, new AdaptadorImpuesto()).create();
            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(10, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .build();
            Retrofit client = new Retrofit.Builder()
                    .baseUrl(Utilidades.obtenerURLWebService())
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            servicioImpuesto = client.create(ServicioImpuesto.class);
        }
        return servicioImpuesto;
    }

    public interface ServicioImpuesto {
        @GET("tarifaImpuesto/obtenerTarifasImpuestoPorTipoImpuesto/{idTarifaImpuesto}")
        Call<List<TarifasImpuesto>> obtenerTarifasImpuestoPorTipoImpuesto(@Path("idTarifaImpuesto") Integer idTarifaImpuesto);
    }

}
