art {
    embeddedModules {
        applicationCore()
        applicationEntity()
        applicationLogging()
        applicationService()
        applicationConfig()
        applicationConfigTypesafe()
        applicationConfigYaml()
        applicationConfigGroovy()
        applicationConfigRemote()
        applicationConfigRemoteApi()
        applicationConfiguratorApi()
        applicationScheduler()
        applicationConfigExtensions()
        applicationProtobuf()
        applicationProtobufGenerated()
        applicationGrpc()
        applicationGrpcClient()
        applicationGrpcServer()
        applicationJson()
        applicationXml()
        applicationHttp()
        applicationSoap()
        applicationMetrics()
        applicationMetricsHttp()
        applicationHttpJson()
        applicationHttpXml()
        applicationHttpClient()
        applicationHttpServer()
        applicationSoapClient()
        applicationSoapServer()
        applicationSql()
        applicationRocksDb()
        applicationExampleApi()
        applicationRsocket()
        applicationReactiveService()
    }
}

dependencies {
    embedded("com.oracle", "ojdbc6", "11.+")
}