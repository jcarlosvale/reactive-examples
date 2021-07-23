package course.springframework.reactiveexamples;

import course.springframework.reactiveexamples.domain.Person;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class PersonRepositoryImplTest {

    PersonRepository repository;

    @BeforeEach
    void setUp() {
        repository = new PersonRepositoryImpl();
    }

    @Test
    void getById() {
        Mono<Person> personMono = repository.getById(1);

        Person person = personMono.block();

        System.out.println(person);
    }

    @Test
    void getByIdSubscribe() {
        Mono<Person> personMono = repository.getById(1);

        personMono.subscribe(System.out::println);
    }

    @Test
    void getByIdMapSubscribe() {
        Mono<Person> personMono = repository.getById(1);

        personMono.map(Person::getFirstName).subscribe(System.out::println);
    }

    @Test
    void fluxTestBlockFirst() {
        Flux<Person> personFlux = repository.findAll();

        Person person = personFlux.blockFirst();

        System.out.println(person);
    }

    @Test
    void testFluxSubscribe() {
        Flux<Person> personFlux = repository.findAll();

        personFlux.subscribe(System.out::println);
    }

    @Test
    void testFluxToListMono() {

        Flux<Person> personFlux = repository.findAll();

        Mono<List<Person>> personListMono = personFlux.collectList();

        personListMono.subscribe(list -> list.forEach(System.out::println));
    }

    @Test
    void testFindPersonById() {
        Flux<Person> personFlux = repository.findAll();

        final Integer id = 3;

        Mono<Person> personMono = personFlux.filter(person -> person.getId().equals(id)).next();

        personMono.subscribe(System.out::println);
    }

    @Test
    void testFindPersonByIdNotFound() {
        Flux<Person> personFlux = repository.findAll();

        final Integer id = 0;

        Mono<Person> personMono = personFlux.filter(person -> person.getId().equals(id)).next();

        personMono.subscribe(System.out::println);
    }

    @Test
    void testFindPersonByIdNotFoundWithException() {
        Flux<Person> personFlux = repository.findAll();

        final Integer id = 0;

        Mono<Person> personMono = personFlux.filter(person -> person.getId().equals(id)).single();

        personMono
                .doOnError(throwable -> System.out.println("I went boom"))
                .onErrorReturn(Person.builder().id(id).build())
                .subscribe(System.out::println);
    }
}