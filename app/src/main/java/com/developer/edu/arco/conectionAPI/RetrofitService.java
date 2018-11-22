package com.developer.edu.arco.conectionAPI;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

public interface RetrofitService {

    //login--------------------------------------------------------------------

    @GET("/loginDocente/{EMAIL}/{SENHA}")
    Call<String> logarDocente(@Path("EMAIL") String EMAIL,
                              @Path("SENHA") String SENHA);

    @GET("/loginDiscente/{EMAIL}/{SENHA}")
    Call<String> logarDiscente(@Path("EMAIL") String EMAIL,
                               @Path("SENHA") String SENHA);


    //user-----------------------------------------------------------------------

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


    //ARCO ------------------------------------------------------------------------------------------------

    @POST("arco/novoArco/{JSON}")
    Call<String> novoArco(@Header("token") String TOKIENAPI,
                          @Path("JSON") String json);

    @GET("arco/buscarArcoDiscente/{DISCENTE_ID}")
    Call<String> buscarMeusArcosDiscente(@Header("token") String TOKIENAPI,
                                         @Path("DISCENTE_ID") String DISCENTE_ID);


    @GET("/arco/bucarArcosCompartilhados")
    Call<String> buscarArcosCompartilhados(@Header("token") String TOKIENAPI);


    @PUT("/arco/compartilharArco/{ID}/{COMPARTILHADO}")
    Call<String> atulizarCompartilhamento(@Header("token") String TOKIENAPI,
                                          @Path("ID") String ID,
                                          @Path("COMPARTILHADO") String COMPARTILHADO);

    @DELETE("/arco/excluirArco/{ID}")
    Call<String> deletarArco(@Header("token") String TOKIENAPI,
                             @Path("ID") String ID);

    @GET("/arco/buscarArcoDocente/{DOCENTE_ID}")
    Call<String> buscarMeusArcosDocente(@Header("token") String TOKIENAPI,
                                        @Path("DOCENTE_ID") String DOCENTE_ID);

    @GET("/arco/buscarSolicitacoes")
    Call<String> buscarSolicitacoes(@Header("token") String TOKIENAPI);

    @PUT("/arco/aceitarSolicitacao/{ID}/{ARCO_ID}")
    Call<String> aceitarSolicao(@Header("token") String TOKIENAPI,
                                @Path("ID") String id,
                                @Path("ARCO_ID") String arco_id);

//ETAPA ---------------------------------------------------------------


    @PUT("/etapa/aprovarEtapa/{ID}/{PROX_ID}/{ARCO_ID}")
    Call<String> aprovar(@Header("token") String TOKIENAPI,
                         @Path("ID") String id,
                         @Path("PROX_ID") String prox_id,
                         @Path("ARCO_ID") String arco_id);

    @PUT("/etapa/reprovarEtapa/{ID}")
    Call<String> reprovar(@Header("token") String TOKIENAPI,
                          @Path("ID") String id);

    @PUT("/etapa/submeterEtapa/{ID}/{RESUMO}")
    Call<String> submeter(@Header("token") String TOKIENAPI,
                          @Path("ID") String id,
                          @Path("RESUMO") String resumo);

    @PUT("/etapa/salvarEtapa/{ID}/{RESUMO}/")
    Call<String> salvar(@Header("token") String TOKIENAPI,
                        @Path("ID") String id,
                        @Path("RESUMO") String resumo);


    @GET("/etapa/listarEtapasArco/{ARCO_ID}")
    Call<String> buscarEtapasArco(@Header("token") String TOKIENAPI,
                                  @Path("ARCO_ID") String ARCO_ID);

    //--------------------------------------------------------------------------

    ///:NOME/:CAMINHO/:ETAPA_ID/:ETAPA_ARCO_ID/:BASE64

    @Multipart
    @POST("/documento/{JSON}")
    Call<String> novoArquivoEtapa(@Header("token") String TOKIENAPI,
                                  @Part("JSON") String json);

    @Multipart
    @POST("/upp/upload/{ETAPA_ID}/{ARCO_ID}")
    Call<ResponseBody> upload(@Header("token") String TOKIENAPI,
                              @Part MultipartBody.Part file,
                              @Part("file") RequestBody name,
                              @Path("ETAPA_ID") String ETAPA_ID,
                              @Path("ARCO_ID") String ARCO_ID);



    @GET("/documento/buscarArquivos/{ETAPA_ID}")
    Call<String> buscarArquivosEtapa(@Header("token") String TOKIENAPI,
                                        @Path("ETAPA_ID") String ETAPA_ID);

    @DELETE("/documento/apagarArquivosEtapa/{ID}")
    Call<String> apagarArquivosEtapa(@Header("token") String TOKIENAPI,
                                     @Path("ID") String ID);




}
