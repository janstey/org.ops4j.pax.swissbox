/*
 * Copyright 2009 Alin Dreghiciu.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ops4j.pax.swissbox.converter.java.lang;

import org.osgi.service.blueprint.container.Converter;
import org.osgi.service.blueprint.container.ReifiedType;
import static org.ops4j.pax.swissbox.converter.internal.Primitives.*;
import static org.ops4j.pax.swissbox.converter.java.lang.AssignableConverter.*;
import static org.ops4j.pax.swissbox.converter.java.lang.FromNullConverter.*;

/**
 * JAVADOC
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 */
public class ToStringConverter
    implements Converter
{

    public static final ToStringConverter INSTANCE = new ToStringConverter();

    public boolean canConvert( final Object sourceObject,
                               final ReifiedType targetType )
    {
        return fromNullConverter().canConvert( sourceObject, targetType )
               || assignableConverter().canConvert( sourceObject, targetType )
               || String.class.isAssignableFrom( unwrap( targetType.getRawClass() ) );
    }

    public Object convert( final Object sourceObject,
                           final ReifiedType targetType )
        throws Exception
    {
        if( !canConvert( sourceObject, targetType ) )
        {
            throw new Exception(
                String.format(
                    "%s cannot convert an %s", ToStringConverter.class.getSimpleName(), sourceObject.getClass()
                )
            );
        }

        if( fromNullConverter().canConvert( sourceObject, targetType ) )
        {
            return fromNullConverter().convert( sourceObject, targetType );
        }

        if( assignableConverter().canConvert( sourceObject, targetType ) )
        {
            return assignableConverter().convert( sourceObject, targetType );
        }

        return convertToString( sourceObject );
    }

    public String convertToString( final Object sourceObject )
        throws Exception
    {
        if( sourceObject instanceof Class )
        {
            return ( (Class) sourceObject ).getName();
        }

        return sourceObject.toString();
    }

    public static ToStringConverter toStringConverter()
    {
        return INSTANCE;
    }

}