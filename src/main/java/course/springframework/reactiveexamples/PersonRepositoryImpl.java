package course.springframework.reactiveexamples;

import course.springframework.reactiveexamples.domain.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PersonRepositoryImpl implements PersonRepository{

    Person somePerson = new Person(1, "Some", "Person");
    Person alice = new Person(2, "Alice", "Some");
    Person bob = new Person(3, "Bob", "Person");
    Person charlie = new Person(3, "Charlie", "Surname");


    @Override
    public Mono<Person> getById(Integer id) {
        return Mono.just(somePerson);
    }

    @Override
    public Flux<Person> findAll() {
        return Flux.just(somePerson, alice, bob, charlie);
    }
}
