package org.migus.web.data.ibatis.types;

import java.sql.SQLException;
import java.util.UUID;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

public class UUIDTypeHandlerCallback implements TypeHandlerCallback {

	public Object getResult(ResultGetter getter) throws SQLException {
		String value = getter.getString();

		if (getter.wasNull()) {
			return null;
		}
		return this.valueOf(value);
	}

	public void setParameter(ParameterSetter setter, Object parameter)
			throws SQLException {
		if (parameter instanceof UUID) {
			UUID uuid = (UUID) parameter;

			setter.setString(uuid.toString());
		} else {
			throw new IllegalArgumentException("parameter is not type "
					+ UUID.class.getName());
		}

	}

	public Object valueOf(String s) {
		return UUID.fromString(s);
	}

}
