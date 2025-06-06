package com.example.myfootballcollectionkmp.database

import com.example.myfootballcollectionkmp.database.dao.UserDao
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.postgres.PostgresPlugin
import org.jdbi.v3.sqlobject.SqlObjectPlugin
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

@Configuration
class DatabaseConfiguration {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    fun driverManagerDataSource(): DataSource = DriverManagerDataSource()

    @Bean
    fun dataSourceTransactionManager(dataSource: DataSource?): DataSourceTransactionManager {
        val dataSourceTransactionManager = DataSourceTransactionManager()
        dataSourceTransactionManager.dataSource = dataSource
        return dataSourceTransactionManager
    }

    @Bean
    fun jdbi(dataSource: DataSource?): Jdbi =
        Jdbi.create(dataSource)
            .installPlugin(SqlObjectPlugin())
            .installPlugin(PostgresPlugin())
            .installPlugin(KotlinPlugin())

    @Bean
    fun getUserDao(jdbi: Jdbi): UserDao = jdbi.onDemand(UserDao::class.java)
}