package com.wdpr.cups.conf;

import java.util.Set;

public interface ObjectReaderIF <T>{
	Set <T> readObject();
	void writeObject(Set <T> t);
}
