package net.iskandar.murano_example.dal;

import java.util.List;

import net.iskandar.murano_example.domain.Position;

public interface PositionDao {
	
	List<Position> list();
	Position get(Integer positionId);

}
