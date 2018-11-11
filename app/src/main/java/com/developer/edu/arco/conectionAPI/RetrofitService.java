package com.developer.edu.arco.conectionAPI;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitService {

    @GET("/loginDocente/{EMAIL}/{SENHA}")
    Call<String> logarDocente(@Path("EMAIL") String EMAIL,
                              @Path("SENHA") String SENHA);

    @GET("/loginDiscente/{EMAIL}/{SENHA}")
    Call<String> logarDiscente(@Path("EMAIL") String EMAIL,
                               @Path("SENHA") String SENHA);


    @POST("discente/{NOME}/{INSTITUICAO}/{EMAIL}/{SENHA}")
    Call<String> cadastarDiscente(@Header("token") String TOKIENAPI,
                                  @Path("NOME") String NOME,
                                  @Path("INSTITUICAO") String INSTITUICAO,
                                  @Path("EMAIL") String EMAIL,
                                  @Path("SENHA") String SENHA);


    @POST("docente/{NOME}/{FORMACAO}/{EMAIL}/{SENHA}")
    Call<String> cadastarDocente(@Header("token") String TOKIENAPI,
                                 @Path("NOME") String NOME,
                                 @Path("FORMACAO") String FORMACAO,
                                 @Path("EMAIL") String EMAIL,
                                 @Path("SENHA") String SENHA);

    @GET("docente/list")
    Call<String> buscarTodosDocentes(@Header("token") String TOKIENAPI);

    @GET("discente/list")
    Call<String> buscarTodosDiscentes(@Header("token") String TOKIENAPI);

    @POST("arco/{json}")
    Call<String> novoArco(@Header("token") String TOKIENAPI,
                          @Path("json") String json);


    @GET("/arco/{DISCENTE_ID}")
    Call<String> buscarMeusArcosDiscente(@Header("token") String TOKIENAPI,
                                         @Path("DISCENTE_ID") String DISCENTE_ID);

    @GET("/etapa/{ID}")
    Call<String> buscarEtapasArco(@Header("token") String TOKIENAPI,
                                  @Path("ID") String ARCO_ID);


    @GET("/arco2/compartilhados")
    Call<String> buscarArcosCompartilhados(@Header("token") String TOKIENAPI);


    @PUT("/arco/{ID}/{COMPARTILHADO}")
    Call<String> atulizarCompartilhamento(@Header("token") String TOKIENAPI,
                                          @Path("ID") String ID,
                                          @Path("COMPARTILHADO") String COMPARTILHADO);

}
