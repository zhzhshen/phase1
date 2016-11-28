Bootstrap:
1. register container with class(register, resolve setup)
    1. class without registration throw NoRegistrationException[x] 7 8.5
    2. class not registered throw exception[x] 3 2
    3. with registration will resolve return no exception[x] 7 5
2. bind named instance(bind)
    1. inject without binding will inject default instance[x] 10 22.5
    2. inject with binding will inject specifically bind instance[x] 5 6
    3. bind instance of other class throw exception[x] 5 6

Basic Injection:
1. Inject constructors
    1. more than one @inject annotation throw exception[x] 13
    2. no annotation return default constructor instance[x] 4
    3. one annotation resolve
        1. bind named instance[x] 10 11.5
        2. inject other class instances[x] 5 7
2. Inject fields
    1. no fields with annotation return[x] 5 2
    2. Refactor[x] 10
    3. resolve every annotated field[x] 10 10
    4. resolve field with @named[x] 7 4
3. Inject methods
    1. parameter without named[x] 12 15
    2. named parameter[x] 5 4
    3. multiple inject method[x] 5 5

Select the implementation:
1. Qualifier[x] 15 46
2. Inject with name annotation - covered

Provider methods:
1. provider return default constructor injection[x] 15 33

Singleton Injection[x] 15 13

Circular Dependency

Scopes
