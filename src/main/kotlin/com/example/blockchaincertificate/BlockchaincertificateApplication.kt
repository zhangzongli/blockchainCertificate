package com.example.blockchaincertificate

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@SpringBootApplication
@MapperScan(basePackages = ["com.example.blockchaincertificate.**.mapper"])
class BlockchaincertificateApplication {

    @Bean
    fun getEtherscanService(): EtherscanService {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://api-rinkeby.etherscan.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return retrofit.create(EtherscanService::class.java)
    }
}

fun main(args: Array<String>) {
    runApplication<BlockchaincertificateApplication>(*args)
}


