package com.wdpr.cups.command;

import java.util.List;
import java.util.Set;

public interface Command<T> {
	Set<List<String>> add(Set<T> t);
	Set<List<String>> delete(Set<T> t);
	Set<List<String>> update(Set<T> t);
}
