# binpack
linear bin pack library


A library that can explore the solutions to the [stock cutting problem](https://en.wikipedia.org/wiki/Cutting_stock_problem) as a general one dimenational bin packing algorithm. 

The premise assumes you have 'stock lengths' available and that you have lengths that you wish to fit into the least 
number of these 'stock lengths.' There is affordance for using up existing stock of limited supply. 


Run gradlew or gradlew.bat from console to build and test project.

    somepath\binpack> gradlew build

To use in your build:

[ ![Download](https://api.bintray.com/packages/jnellis/maven/binpack/images/download.svg) ](https://bintray.com/jnellis/maven/binpack/_latestVersion)

maven:

    <dependency>
      <groupId>net.jnellis</groupId>
      <artifactId>binpack</artifactId>
      <version>1.3</version>
      <type>pom</type>
    </dependency>

gradle:

    repositories {
      jcenter() 
    }
    dependencies {
      compile 'net.jnellis:binpack:1.3'
    }
    
    
