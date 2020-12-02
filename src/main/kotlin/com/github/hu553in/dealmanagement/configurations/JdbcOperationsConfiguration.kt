package com.github.hu553in.dealmanagement.configurations

import org.springframework.boot.autoconfigure.flyway.FlywayDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource

@Configuration
class JdbcOperationsConfiguration {
    @Bean
    @FlywayDataSource
    @ConfigurationProperties(prefix = "spring.data-source.deal-management")
    fun dataSource(): DataSource = DataSourceBuilder.create().build()

    @Bean
    fun jdbcOperations(dataSource: DataSource): JdbcOperations = JdbcTemplate(dataSource)
}
