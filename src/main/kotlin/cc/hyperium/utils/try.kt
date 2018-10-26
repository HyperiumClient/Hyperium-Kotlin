package cc.hyperium.utils

sealed class Try<out A> {
    inline fun <B> map(mapper: (A) -> B): Try<B> {
        return when (this) {
            is Failure -> this
            is Success -> Try { mapper(this.value) }
        }
    }

    inline fun <B> force(failureVal: B, successVal: (A) -> B): Try<B> {
        return when (this) {
            is Failure -> Success(failureVal)
            is Success -> map(successVal)
        }
    }

    inline fun resolve(failure: () -> Unit): A? {
        return when (this) {
            is Failure -> {
                failure()
                return null
            }
            is Success -> this.value
        }
    }

    companion object {
        inline operator fun <A> invoke(body: () -> A?): Try<A> {
            return try {
                val res = body()

                if (res == null) Failure else Success(res)
            } catch (e: Exception) {
                Failure
            }
        }
    }
}

class Success<out A>(val value: A) : Try<A>()
object Failure : Try<Nothing>()