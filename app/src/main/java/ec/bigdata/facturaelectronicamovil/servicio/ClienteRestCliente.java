package ec.bigdata.facturaelectronicamovil.servicio;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import ec.bigdata.facturaelectronicamovil.modelo.Cliente;
import ec.bigdata.facturaelectronicamovil.utilidad.Utilidades;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by DavidLeonardo on 6/5/2016.
 */
public class ClienteRestCliente {

    private static ServicioCliente servicioCliente;

    public static ServicioCliente getServicioCliente() {
        if (servicioCliente == null) {
            Gson gson = new GsonBuilder().setDateFormat("yyyy/MM/dd").create();
            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .build();
            Retrofit client = new Retrofit.Builder()
                    .baseUrl(Utilidades.obtenerURLWebService())
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))

                    .build();
            servicioCliente = client.create(ClienteRestCliente.ServicioCliente.class);
        }
        return servicioCliente;
    }

    public interface ServicioCliente {

        @GET("cliente/clientes-empresa/{idEmpresa}/{resultadoMinimo}/{resultadoMaximo}")
        Call<List<ec.bigdata.facturaelectronicamovil.modelo.Cliente>> obtenerClientesPorEmpresaAsociado(@Path("idEmpresa") String idEmpresa, @Path("resultadoMinimo") Integer resultadoMinimo, @Path("resultadoMaximo") Integer resultadoMaximo);

        @GET("cliente/clientes-validacion-identificacion/{idEmpresa}/{idCliente}")
        Call<Cliente> obtenerClientePorIdentificacionYEmpresaAsociado(@Path("idEmpresa") String idEmpresa, @Path("idCliente") String idCliente);

        @GET("cliente/clientes-validacion-correo/{idEmpresa}/{correoPrincipal}")
        Call<Cliente> obtenerClientePorCorreoPrincipalYPorEmpresaAsociado(@Path("idEmpresa") String idEmpresa, @Path("correoPrincipal") String correoPrincipal);

        @POST("cliente/clientes/{clienteActualizado}")
        Call<ResponseBody> actualizarCliente(@Body Cliente cliente);

        @POST("cliente/clientes/{identificacionCliente}/{idEmpresa}/{razonSocial}/{direccion}/{telefonoPrincipal}/{correoPrincipal}")
        Call<ResponseBody> guardarCliente(@Query(value = "identificacionCliente", encoded = true) String identificacionCliente,
                                          @Query(value = "idEmpresa", encoded = true) String idEmpresa,
                                          @Query(value = "razonSocial", encoded = true) String razonSocial,
                                          @Query(value = "direccion", encoded = true) String direccion,
                                          @Query(value = "telefonoPrincipal", encoded = true) String telefonoPrincipal,
                                          @Query(value = "correoPrincipal", encoded = true) String correoPrincipal);
    }
}
