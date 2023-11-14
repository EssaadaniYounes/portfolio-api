package dev.younes.portfolio.mappers;

public interface IMapper<F, T>{
    T mapTo(F f);

    F mapFrom(T t);

}
