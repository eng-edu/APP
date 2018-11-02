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


    //MANTER CATADOR ---------------------------------------------------------------------
    @POST("catador/{CPF}/{NOME}/{TELEFONE}/{ENDERECO}/{DATA_NASCIMENTO}/{ASSOC_COOP_ID}")
    Call<String> cadastarCatador(@Header("token") String tokenAPI,
                                 @Path("CPF") String CPF,
                                 @Path("NOME") String NOME,
                                 @Path("TELEFONE") String TELEFONE,
                                 @Path("ENDERECO") String ENDERECO,
                                 @Path("DATA_NASCIMENTO") String DATA_NASCIMENTO,
                                 @Path("ASSOC_COOP_ID") String ASSOC_COOP_ID);

    @PUT("catador/{ID}/{CPF}/{NOME}/{TELEFONE}/{ENDERECO}/{DATA_NASCIMENTO}/{ASSOC_COOP_ID}")
    Call<String> alterarCatador(@Header("token") String tokenAPI,
                                @Path("ID") String ID,
                                @Path("CPF") String CPF,
                                @Path("NOME") String NOME,
                                @Path("TELEFONE") String TELEFONE,
                                @Path("ENDERECO") String ENDERECO,
                                @Path("DATA_NASCIMENTO") String DATA_NASCIMENTO,
                                @Path("ASSOC_COOP_ID") String ASSOC_COOP_ID);

    @DELETE("catador/{ID}/{ASSOC_COOP_ID}")
    Call<String> excluirCatador(@Header("token") String tokenAPI,
                                @Path("ID") String ID,
                                @Path("ASSOC_COOP_ID") String ASSOC_COOP_ID);
}
