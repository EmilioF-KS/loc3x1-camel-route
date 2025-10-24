package com.ei.camel.mappings;

import org.mapstruct.Mapper;

@Mapper
public interface RandLocationToCimLocationMapMapper {
    Object map(Object source);
}
