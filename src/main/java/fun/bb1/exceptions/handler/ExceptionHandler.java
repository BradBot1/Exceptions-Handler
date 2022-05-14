package fun.bb1.exceptions.handler;

import java.util.function.Function;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 
 * Copyright 2022 BradBot_1
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * A simple way to manage exceptions
 * 
 * @author BradBot_1
 */
public final class ExceptionHandler<T> implements Supplier<T> {
	
	public static final <T> T handle(@NotNull final IThrowingSupplier<T> supplier) {
		return new ExceptionHandler<T>(supplier).invoke().get();
	}
	
	public static final <T> T handle(@NotNull final IThrowingSupplier<T> supplier, T fallback) {
		return new ExceptionHandler<T>(supplier).catcher((t)->fallback).invoke().get();
	}
	
	private final @NotNull IThrowingSupplier<T> supplier;
	private @NotNull Function<Throwable, T> catcher = (t)->null;
	private @Nullable T value;
	
	public ExceptionHandler(@NotNull final IThrowingSupplier<T> supplier) {
		this.supplier = supplier;
	}
	
	public ExceptionHandler<T> catcher(@NotNull final Function<Throwable, T> catcher) {
		this.catcher = catcher;
		return this;
	}
	
	public ExceptionHandler<T> invoke() {
		try {
			this.value = supplier.get();
		} catch (Throwable t) {
			this.value = this.catcher.apply(t);
		}
		return this;
	}
	
	public @Nullable T get() {
		return this.value;
	}
	
}
