# Keycloak deployment issue

This repository reproduces a keycloak SPI deployment error.

The repository has two important folders:

 - `jar-module` contains the SPI implementation
 - `ear-module` packages up the SPI implementation and associated dependencies.
 
 Running the Maven build will produce an EAR file that can be deployed to keycloak as a extension.

## Instructions

1. Checkout repository
2. Run `mvn package` to produce an EAR file
3. Copy the EAR from `/ear-module/targets/` into `<keycloak-deployment-7.0.1>/standalone/deployments`
4. Trigger a deployment, e.g. start keycloak or if hot deployment is running, check logs.
4. Check server.xml logs and see error:

```
2019-11-10 22:11:36,790 ERROR [org.jboss.msc.service.fail] (ServerService Thread Pool -- 56) MSC000001: Failed to start service jboss.deployment.unit."keycloak-server.war".undertow-deployment: org.jboss.msc.service.StartException in service jboss.deployment.unit."keycloak-server.war".undertow-deployment: java.lang.RuntimeException: RESTEASY003325: Failed to construct public org.keycloak.services.resources.KeycloakApplication(javax.servlet.ServletContext,org.jboss.resteasy.core.Dispatcher)
        at org.wildfly.extension.undertow.deployment.UndertowDeploymentService$1.run(UndertowDeploymentService.java:81)
        at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)
        at java.util.concurrent.FutureTask.run(FutureTask.java:266)
        at org.jboss.threads.ContextClassLoaderSavingRunnable.run(ContextClassLoaderSavingRunnable.java:35)
        at org.jboss.threads.EnhancedQueueExecutor.safeRun(EnhancedQueueExecutor.java:1982)
        at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.doRunTask(EnhancedQueueExecutor.java:1486)
        at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.run(EnhancedQueueExecutor.java:1377)
        at java.lang.Thread.run(Thread.java:748)
        at org.jboss.threads.JBossThread.run(JBossThread.java:485)
Caused by: java.lang.RuntimeException: RESTEASY003325: Failed to construct public org.keycloak.services.resources.KeycloakApplication(javax.servlet.ServletContext,org.jboss.resteasy.core.Dispatcher)
        at org.jboss.resteasy.core.ConstructorInjectorImpl.construct(ConstructorInjectorImpl.java:164)
        at org.jboss.resteasy.spi.ResteasyProviderFactory.createProviderInstance(ResteasyProviderFactory.java:2784)
        at org.jboss.resteasy.spi.ResteasyDeployment.createApplication(ResteasyDeployment.java:364)
        at org.jboss.resteasy.spi.ResteasyDeployment.startInternal(ResteasyDeployment.java:277)
        at org.jboss.resteasy.spi.ResteasyDeployment.start(ResteasyDeployment.java:89)
        at org.jboss.resteasy.plugins.server.servlet.ServletContainerDispatcher.init(ServletContainerDispatcher.java:119)
        at org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher.init(HttpServletDispatcher.java:36)
        at io.undertow.servlet.core.LifecyleInterceptorInvocation.proceed(LifecyleInterceptorInvocation.java:117)
        at org.wildfly.extension.undertow.security.RunAsLifecycleInterceptor.init(RunAsLifecycleInterceptor.java:78)
        at io.undertow.servlet.core.LifecyleInterceptorInvocation.proceed(LifecyleInterceptorInvocation.java:103)
        at io.undertow.servlet.core.ManagedServlet$DefaultInstanceStrategy.start(ManagedServlet.java:303)
        at io.undertow.servlet.core.ManagedServlet.createServlet(ManagedServlet.java:143)
        at io.undertow.servlet.core.DeploymentManagerImpl$2.call(DeploymentManagerImpl.java:583)
        at io.undertow.servlet.core.DeploymentManagerImpl$2.call(DeploymentManagerImpl.java:554)
        at io.undertow.servlet.core.ServletRequestContextThreadSetupAction$1.call(ServletRequestContextThreadSetupAction.java:42)
        at io.undertow.servlet.core.ContextClassLoaderSetupAction$1.call(ContextClassLoaderSetupAction.java:43)
        at org.wildfly.extension.undertow.security.SecurityContextThreadSetupAction.lambda$create$0(SecurityContextThreadSetupAction.java:105)
        at org.wildfly.extension.undertow.deployment.UndertowDeploymentInfoService$UndertowThreadSetupAction.lambda$create$0(UndertowDeploymentInfoService.java:1502)
        at org.wildfly.extension.undertow.deployment.UndertowDeploymentInfoService$UndertowThreadSetupAction.lambda$create$0(UndertowDeploymentInfoService.java:1502)
        at org.wildfly.extension.undertow.deployment.UndertowDeploymentInfoService$UndertowThreadSetupAction.lambda$create$0(UndertowDeploymentInfoService.java:1502)
        at org.wildfly.extension.undertow.deployment.UndertowDeploymentInfoService$UndertowThreadSetupAction.lambda$create$0(UndertowDeploymentInfoService.java:1502)
        at io.undertow.servlet.core.DeploymentManagerImpl.start(DeploymentManagerImpl.java:596)
        at org.wildfly.extension.undertow.deployment.UndertowDeploymentService.startContext(UndertowDeploymentService.java:97)
        at org.wildfly.extension.undertow.deployment.UndertowDeploymentService$1.run(UndertowDeploymentService.java:78)
        ... 8 more
Caused by: java.util.ServiceConfigurationError: org.keycloak.storage.ldap.mappers.LDAPStorageMapperFactory: Provider org.keycloak.storage.ldap.mappers.FullNameLDAPStorageMapperFactory not a subtype
        at java.util.ServiceLoader.fail(ServiceLoader.java:239)
        at java.util.ServiceLoader.access$300(ServiceLoader.java:185)
        at java.util.ServiceLoader$LazyIterator.nextService(ServiceLoader.java:376)
        at java.util.ServiceLoader$LazyIterator.next(ServiceLoader.java:404)
        at java.util.ServiceLoader$1.next(ServiceLoader.java:480)
        at org.keycloak.provider.DefaultProviderLoader.load(DefaultProviderLoader.java:60)
        at org.keycloak.provider.ProviderManager.load(ProviderManager.java:93)
        at org.keycloak.services.DefaultKeycloakSessionFactory.loadFactories(DefaultKeycloakSessionFactory.java:214)
        at org.keycloak.services.DefaultKeycloakSessionFactory.init(DefaultKeycloakSessionFactory.java:80)
        at org.keycloak.services.resources.KeycloakApplication.createSessionFactory(KeycloakApplication.java:327)
        at org.keycloak.services.resources.KeycloakApplication.<init>(KeycloakApplication.java:118)
        at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
        at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
        at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
        at java.lang.reflect.Constructor.newInstance(Constructor.java:423)
        at org.jboss.resteasy.core.ConstructorInjectorImpl.construct(ConstructorInjectorImpl.java:152)
        ... 31 more
```

## Why build the extension

Because I want the Access Token that is generated by Keycloak to have a custom `Subject` that matches an external System ID, and not the internal keycloak Identifier (f:foo:externalId)