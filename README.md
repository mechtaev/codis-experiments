## Installation

1. Build CODIS [library](https://github.com/mechtaev/codis).
2. Install CODIS into Maven:

        mvn install:install-file -Dfile=/path/to/codis/target/codis-1.0.jar \
                                 -DgroupId=sg.edu.nus.comp.codis \
                                 -DartifactId=codis \
                                 -Dversion=1.0 \
                                 -Dpackaging=jar
3. Execute `mvn package`.
4. Run to see help message:

        java -jar target/codisexp-1.0-jar-with-dependencies.jar
