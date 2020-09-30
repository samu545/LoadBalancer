# Load Balancer :- 
## Goal - 
A load balancer is a component that, once invoked, it distributes incoming requests to a list of registered providers and return the value obtained from one of the registered providers to the original caller. 

### Build and run :
````
cd loadBalancerCore/
mvn clean package
cd /target
java -jar load-balancer-core-1.0.0.jar
````

### Github Actions Publish :

Maven to publish Java packages to a registry as part of your continuous integration (CI) github actions workflow
using mavenpublish.yml playbook definition.
Ref : [GitHub Actions](https://docs.github.com/en/free-pro-team@latest/actions/guides/publishing-java-packages-with-maven)

## Scope of implementations

Basic core implementation of Random and Round robin load balancer is implement with plane old java core libs.

### get()  : 
retrieve an unique identifier (string) of the provider. For simplicity a host Ip address.
   
#### Two strategies are implement for this fulfillment.
   
a. RoundRobinLoadBalancer : will round robin the list of active providers from the pool and get the next Ip provider string.

b. RandomLoadBalancer : will randomly get the provider from the pool of active provider list.

### add()  :
To add a provider to the pool. This method is to facilitate manual inclusion to provider pool.
Employs max providerPool capacity validation in here 10.

### remove()  : 
To remove a provider from the pool. This is to facilitate manual exclusion of provider from the pool.

### heartbeat checker  : 

Customer TimerTask implementation for health check monitoring and update the provider state.
The strategy employed here is.
ProviderPool is a Map<Provider, HeartBeatCount> 
Active : HeartBeatCount = 2
inActive : HeartBeatCount < 2 

TimerTask will execute every X seconds (here 30 secs) and check the heartbeat of each provider both active and inactive ones and below logic is employed on it.

### check()  : 
Basically this should a native call to check the state/health of provider.
Just for simplification and stochastic behaviour, employed a RandomBoolean() strategy to check the health of provider.

if check fails ?
 
Step 1: i.e provider is inActive - heartbeat count is set = 0

And if check passes ?

Step 1: i.e provider is active then  heartbeat count is checked.

Step 2 : And if heartBeat count is less than 2 then increment it by 1.
This help us to employ the consecutive time check on heartbeat to affirm that the provider is active.

Step 3: Else we dont have to do anything its already active.     

#### Not clear on pointer no: 8 (Cluster Capacity Limit) :-
This sounds to me like a RateLimiter and Circuit breaker. 
This step that is not completely clear. Imagine there are 10 providers and every provider handles 10 requests, 
so the cluster has the capacity to handle 100 requests. So, if the 101 request comes in, what are the consequences, so an error be thrown or should it be in queue to handle later?
In this areas, things coming to mind are
1. what is response classification ?
2. Do we employ Retries ?
    - retry budget
    - retry backoff
    - retry timeouts.
    
But, the current architecture is designed in such a way that, it can be extended to all the scenarios.


#### Good Strategies for distributed system :-

Ref : [Finagle List of Load Balancer](https://twitter.github.io/finagle/guide/Clients.html#load-balancing)

Ref : [Finagle Aperture Load Balancer](https://twitter.github.io/finagle/guide/ApertureLoadBalancers.html)