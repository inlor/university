# LPC Local Procedure Call

## Install and launch

To install the project:

```sh
$ make
```

To launch the project:

```sh
$ make server # 1st terminal
$ make client # 2nd terminal
```

## Documentation

The full documentation is [here](./docs/README.md).

## Tests

To lauch unit tests you need to install CMocka:

```sh
$ make install-cmocka # sudo is required
$ make check
```

Tests are launched for every new commit, thanks to continuous integration.

