## Scalability-Lab AutoScaling AREP

**Continuos Integration Circle CI**  [![CircleCI](https://circleci.com/gh/AlejandroBohal/AWS-SCALABILITY-LAB.svg?style=svg)](https://circleci.com/gh/AWS-SCALABILITY-LAB)

## Pre requisitos

* **Maven** Para compilar el programa.
* **Git** Para clonar este repositorio.
* **JDK & JRE 8** Para correr el programa.
* **Docker & Docker compose** Para tener una instancia local del programa. 

## Contexto del problema

El problema consiste en el cálculo de números factoriales grandes, el programa cuenta con una implementación iterativa (no dinámica) la idea es que el consumo de cpu sea alto para esto haremos pruebas de carga intensivas al endpoint /factorial/{large-number} la idea de que el consumo de cpu sea alto es poder ver la potencia de Auto escalado de AWS. Puede ver el código fuente de la implementación en este repositorio, y la documentación en la siguiente [GitHub page](google.com) 

Observamos nuestro Dockerfile a continuación:

    ![dockerfile](https://media.discordapp.net/attachments/352624122301513730/773289501698555944/unknown.png?width=1026&height=433)

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
    
3. 