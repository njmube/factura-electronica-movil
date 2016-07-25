package ec.bigdata.facturaelectronicamovil.utilidad;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ec.bigdata.facturaelectronicamovil.modelo.RespuestaSRIMovil;
import ec.bigdata.utilidades.Validaciones;

/**
 * Created by DavidLeonardo on 27/1/2016.
 */
public class Utilidades {
    // public static String IP_DOMINIO_WEB_SERVICE = "http://192.168.10.7:8080/aw_web_service_rest_jersey/";
    public static String IP_DOMINIO_WEB_SERVICE = "http://192.168.10.10:8080/aw_web_service_rest_jersey/";

    private static final String TAG = Utilidades.class.getSimpleName();

    public static final String USUARIO_EMPRESA = "1";
    public static final String USUARIO_PERSONA = "2";

    public static Date obtenerFechaDateFormatoddMMyyyy(String _cadena) {
        Date fecha = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            fecha = simpleDateFormat.parse(_cadena);
        } catch (ParseException ex) {
            Log.e(TAG, "No se pudo formatear la fecha." + ex);
        }
        return fecha;
    }

    public static String obtenerFechaStringFormatoddMMyyyy(Date d) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            simpleDateFormat.setLenient(false);
            String fechaString = simpleDateFormat.format(d);
            return fechaString;
        } catch (Exception ex) {
            Log.e(TAG, "No se pudo formatear la fecha." + ex);
            return null;
        }
    }

    public static Date obtenerFechaFormatoyyyyMMddHHmmss(String _cadenaFecha) {
        try {
            Date fecha = null;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            simpleDateFormat.setLenient(false);
            fecha = simpleDateFormat.parse(_cadenaFecha);
            return fecha;
        } catch (Exception ex) {
            Log.e(TAG, "No se pudo formatear la fecha." + ex);
            return null;
        }
    }

    public static Date obtenerFechaFormatoddMMyyyyHHmmss(String _cadenaFecha) {
        try {
            Date fecha = null;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            simpleDateFormat.setLenient(false);
            fecha = simpleDateFormat.parse(_cadenaFecha);
            return fecha;
        } catch (Exception ex) {
            Log.e(TAG, "No se pudo formatear la fecha." + ex);
            return null;
        }
    }

    public static String obtenerURLWebService() {
        return IP_DOMINIO_WEB_SERVICE + "facturamovil/";
    }

    public static boolean validarIdentificacion(String _identificacion) {
        boolean validado = false;
        if (_identificacion != null && !_identificacion.trim().equals("")) {

            if (Validaciones.isNum(_identificacion)) {

                if (_identificacion.length() == 10) {
                    if (Validaciones.validarCedula(_identificacion)) {
                        validado = true;
                    }
                } else if (_identificacion.length() == 13) {
                    if (Validaciones.validarRucPersonaNatural(_identificacion) || Validaciones.validarRucSociedadPrivada(_identificacion)
                            || Validaciones.validarRucSociedadPublica(_identificacion)) {
                        validado = true;
                    }
                }
            } else {
                validado = true;
            }
        }
        return validado;
    }

    public static void exportFile(File src, File dst) throws IOException {


        FileChannel inChannel = null;
        FileChannel outChannel = null;

        try {
            inChannel = new FileInputStream(src).getChannel();
            outChannel = new FileOutputStream(dst).getChannel();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } finally {
            if (inChannel != null)
                inChannel.close();
            if (outChannel != null)
                outChannel.close();
        }
    }

    public static String formatearRespuestaSRIMovil(RespuestaSRIMovil respuestaSRIMovil) {

        StringBuilder stringBuilderTexto = new StringBuilder();
        if (respuestaSRIMovil != null) {

            if (respuestaSRIMovil.getNumeroComprobante() != null) {
                stringBuilderTexto.append("<p>NÚMERO DE COMPROBANTE:" + respuestaSRIMovil.getNumeroComprobante()).append("</p>");
            }

            if (respuestaSRIMovil.getMensaje() != null) {
                stringBuilderTexto.append("<p>MENSAJE:" + respuestaSRIMovil.getMensaje()).append("</p>");
            }
            if (respuestaSRIMovil.getMensajeAdicional() != null) {
                stringBuilderTexto.append("<p>MENSAJE ADICIONAL:" + respuestaSRIMovil.getMensajeAdicional()).append("</p>");
            }
            if (respuestaSRIMovil.getClaveAcceso() != null) {
                stringBuilderTexto.append("<p>CLAVE DE ACCESO:" + respuestaSRIMovil.getClaveAcceso()).append("</p>");
            }
            if (respuestaSRIMovil.getNumeroAutorizacion() != null) {
                stringBuilderTexto.append("<p>NUMERO DE AUTORIZACIÓN:" + respuestaSRIMovil.getNumeroAutorizacion()).append("</p>");
            }

        }
        return stringBuilderTexto.toString();
    }

    public static void main(String args[]) {


    }

}