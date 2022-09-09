package com.github.lex090.coreapi.data

interface IMapper<in I: Any, out O: Any> {

    fun map(oldData: I): O
}