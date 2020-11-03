## Scalability-Lab AutoScaling AREP

**Continuos Integration Circle CI**  [![CircleCI](https://circleci.com/gh/AlejandroBohal/AWS-SCALABILITY-LAB.svg?style=svg)](https://circleci.com/gh/AWS-SCALABILITY-LAB)

## Pre requisitos

* **Maven** Para compilar el programa.
* **Git** Para clonar este repositorio.
* **JDK & JRE 8** Para correr el programa.
* **Postman** Para probar la infraestructura
* **Docker & Docker compose** Para tener una instancia local del programa. 

## Contexto del problema

El problema consiste en el cálculo de números factoriales grandes, el programa cuenta con una implementación iterativa (no dinámica) la idea es que el consumo de cpu sea alto para esto haremos pruebas de carga intensivas al endpoint /factorial/{large-number} la idea de que el consumo de cpu sea alto es poder ver la potencia de Auto escalado de AWS. Puede ver el código fuente de la implementación en este repositorio, y la documentación en la siguiente [GitHub page](https://alejandrobohal.github.io/AWS-SCALABILITY-LAB/) 

Observamos nuestro Dockerfile a continuación:

![](https://media.discordapp.net/attachments/352624122301513730/773289501698555944/unknown.png?width=1026&height=433)

## AWS AUTO SCALING - TUTORIAL

1. **Cree una instancia de Amazon EC2 (Preferiblemente Ubuntu)**

    Siga estos pasos para  la creación de la máquina
    
    ![](/img/1.png)
    
    ![](/img/2.png)
    
    ![](/img/3.png)
    
2. **Cree una regla de entrada en el puerto 8080 de la máquina**

    Para la creación de la regla de entrada siga estos pasos:
    
    En la nuestra instancia seleccione su respectivo security group.
    
    ![](/img/4.png)
    
    Cree la nueva regla de entrada.
    
    ![](/img/5.png)
    
    Este puerto se encuentra definido en nuestra imágen de docker subida a nuestro repositorio en dockerhub [Docker Hub image](https://hub.docker.com/layers/elcostalitoalegre/scalability/factorial/images/sha256-fa9d524f75ab74d66039183f9e1a0040b66cf3f3f54d7c4b548b10b9517efa2a?context=repo)
    
    Docker-Compose
    
    ![](https://media.discordapp.net/attachments/352624122301513730/773290556096905216/unknown.png?width=822&height=475)
    
3. **Ingrese a la máquina y clone este repositorio**
    
    ```
    ssh -i "<archivo con la llave de la instancia>" ec2-user@<Direccion IP Instancia>
    git clone https://github.com/AlejandroBohal/AWS-SCALABILITY-LAB
    cd AWS-SCALABILITY-LAB
    ```

4. **Instale Docker y docker compose en la máquina EC2 y haga correr la imagen con el docker compose**

    Para instalar Docker ejecute los siguientes comandos:
    
    ```
    sudo apt update
    sudo apt install apt-transport-https ca-certificates curl software-properties-common
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
    sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu focal stable"
    sudo apt install docker-ce
    sudo usermod -aG docker username
    ```
    
    Para instalar Docker-compose ejecute los siguientes comandos:
    
    ```
    sudo curl -L "https://github.com/docker/compose/releases/download/1.27.4/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
    sudo chmod +x /usr/local/bin/docker-compose
    sudo ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose
    ```
    
    Para iniciar el contenedor con el docker-compose ejecute los siguientes comandos:
    
    ![](https://media.discordapp.net/attachments/352624122301513730/773303454516248626/unknown.png?width=814&height=475)
    
5. **Una vez tenga el docker-compose ejecutandose creará un servicio de ubuntu que permita ejecutarlo cuando se inicie la instancia de EC2**
    
    Cree el siguiente archivo en la ruta /etc/systemd/system
    
    ![](https://media.discordapp.net/attachments/352624122301513730/773306336166936636/unknown.png?width=524&height=475)
    
    Una vez creado este archivo, inicie el servicio con los siguientes comandos:

    ```
    systemctl enable docker-service.service
    systemctl start docker-service.service
    ```
    
    
6. **Cree una AMI (Amazon machine imagen) a partir de la instancia EC2 anteriormente creada**
    
    Para crear la AMI siga estos pasos:

    ![](/img/51.png)
    
    Ingrese los datos de configuración básica de una AMI
    
    ![](/img/6.png)

7. **Ahora crearemos una nueva configuración de lanzamiento la cual se utilizará posteriormente por nuestro grupo de autoescalamiento*

    Siga estos pasos para la creación de la configuración de lanzamiento:
    
    ![](/img/7.png)
    
    Ponga un nombre a la configuración y seleccione la AMI anteriormente creada.
    
    ![](/img/8.png)
    
    Seleccione el grupo de seguridad y la llave pública de la máquina anteriormente creada.
    
    ![](/img/10.png)
    
8. **Ahora crearemos un grupo de auto escalamiento que utilice esta configuración de lanzamiento (lanza nodos de la red según la capacidad requerida por el sistema)**

    Siga estos pasos para crear el grupo de auto escalamiento:
    
    ![](/img/9.png)
    
    Seleccione el template de configuración anteriormente creado.
    
    ![](/img/11.png)
    
    Utilice la VPC (Virtual Private Cloud) por defecto de su cuenta de azure, si desea puede crear otra, además seleccione las zonas de disponibilidad a,b y c que representarán una ubicación geográfica diferente para cada nodo de la red. Por ahora no seleccionaremos ningún balanceador de carga (lo crearemos más adelante).
    
    ![](/img/12.png)
    
    Para esta prueba de concepto seleccionaremos un máximo de 3 instancias y para la política de escalamiento pondremos la política de seguimiento de objetivos  (Cuando el porcentaje de uso cpu sea mayor que 50% creará una nueva instancia, por efectos prácticos pondremos un número bajo) como se ve en la siguiente imagen
    
    ![](/img/13.png)

9. **Crearemos un balanceador de carga de aplicación, que nos permita distribuir el tráfico a nivel de HTTP (este contará con su propia DNS y distribuirá la carga entre los diferentes nodos creados por el grupo de auto escalamiento)**

    Para crear el balanceador de carga siga estos pasos:
    
    ![](/img/14.png)
    
    Seleccione un balanceador de carga de aplicación
    
    ![](/img/15.png)
    
    Active las 3 zonas de disponibilidad en el puerto 80, asigne el grupo de seguridad de su primera instancia de EC2 configure el enrutamiento a través de http en el puerto 8080 (o el puerto que le haya asignado en el docker-compose)

    ![](/img/16.png) ![](/img/17.png)
    ![](/img/18.png) ![](/img/19.png)

10. **Editaremos el grupo de autoescalamiento para que use el loadbalancer que acabamos de crear**

    ![](/img/20.png)

Una vez terminados estos pasos nuestra infraestructura ya estará correctamente configurada, para probarla realizaremos pruebas de cargas con postman

## Pruebas de carga

El objetivo de las pruebas de carga es probar que el grupo de auto escalamiento esté funcionando correctamente y cuando encuentre un uso de cpu elevado añada nuevos nodos a la red. 

Para realizar las pruebas de carga con postman seguimos estos pasos:

Cree una colección de postman con un request que exija mucho a la cpu de la máquina de la siguiente manera

![](/img/52.png)
    
![](/img/53.png)
    
![](/img/54.png)
    
En la request pondremos el dns del loadbalancer de aws.

![](/img/55.png)
    
Con el runner de postman ejecutaremos 150 peticiones para poder probar nuestra infraestructura.

![](/img/56.png)
    
## Resultado de pruebas y análisis.








