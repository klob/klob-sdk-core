//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.diandi.klob.sdk.okhttp.callback;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

 public final class Types {
    static final Type[] EMPTY_TYPE_ARRAY = new Type[0];

    private Types() {
    }

    public static ParameterizedType newParameterizedTypeWithOwner(Type ownerType, Type rawType, Type... typeArguments) {
        return new Types.ParameterizedTypeImpl(ownerType, rawType, typeArguments);
    }

    public static GenericArrayType arrayOf(Type componentType) {
        return new Types.GenericArrayTypeImpl(componentType);
    }

    public static WildcardType subtypeOf(Type bound) {
        return new Types.WildcardTypeImpl(new Type[]{bound}, EMPTY_TYPE_ARRAY);
    }

    public static WildcardType supertypeOf(Type bound) {
        return new Types.WildcardTypeImpl(new Type[]{Object.class}, new Type[]{bound});
    }

    public static Type canonicalize(Type type) {
        if(type instanceof Class) {
            Class w3 = (Class)type;
            return (Type)(w3.isArray()?new Types.GenericArrayTypeImpl(canonicalize(w3.getComponentType())):w3);
        } else if(type instanceof ParameterizedType) {
            ParameterizedType w2 = (ParameterizedType)type;
            return new Types.ParameterizedTypeImpl(w2.getOwnerType(), w2.getRawType(), w2.getActualTypeArguments());
        } else if(type instanceof GenericArrayType) {
            GenericArrayType w1 = (GenericArrayType)type;
            return new Types.GenericArrayTypeImpl(w1.getGenericComponentType());
        } else if(type instanceof WildcardType) {
            WildcardType w = (WildcardType)type;
            return new Types.WildcardTypeImpl(w.getUpperBounds(), w.getLowerBounds());
        } else {
            return type;
        }
    }

    public static Class<?> getRawType(Type type) {
        if(type instanceof Class) {
            return (Class)type;
        } else if(type instanceof ParameterizedType) {
            ParameterizedType className2 = (ParameterizedType)type;
            Type rawType = className2.getRawType();
            Preconditions.checkArgument(rawType instanceof Class);
            return (Class)rawType;
        } else if(type instanceof GenericArrayType) {
            Type className1 = ((GenericArrayType)type).getGenericComponentType();
            return Array.newInstance(getRawType(className1), 0).getClass();
        } else if(type instanceof TypeVariable) {
            return Object.class;
        } else if(type instanceof WildcardType) {
            return getRawType(((WildcardType)type).getUpperBounds()[0]);
        } else {
            String className = type == null?"null":type.getClass().getName();
            throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + className);
        }
    }

    static boolean equal(Object a, Object b) {
        return a == b || a != null && a.equals(b);
    }

    public static boolean equals(Type a, Type b) {
        if(a == b) {
            return true;
        } else if(a instanceof Class) {
            return a.equals(b);
        } else if(a instanceof ParameterizedType) {
            if(!(b instanceof ParameterizedType)) {
                return false;
            } else {
                ParameterizedType va3 = (ParameterizedType)a;
                ParameterizedType vb3 = (ParameterizedType)b;
                return equal(va3.getOwnerType(), vb3.getOwnerType()) && va3.getRawType().equals(vb3.getRawType()) && Arrays.equals(va3.getActualTypeArguments(), vb3.getActualTypeArguments());
            }
        } else if(a instanceof GenericArrayType) {
            if(!(b instanceof GenericArrayType)) {
                return false;
            } else {
                GenericArrayType va2 = (GenericArrayType)a;
                GenericArrayType vb2 = (GenericArrayType)b;
                return equals(va2.getGenericComponentType(), vb2.getGenericComponentType());
            }
        } else if(a instanceof WildcardType) {
            if(!(b instanceof WildcardType)) {
                return false;
            } else {
                WildcardType va1 = (WildcardType)a;
                WildcardType vb1 = (WildcardType)b;
                return Arrays.equals(va1.getUpperBounds(), vb1.getUpperBounds()) && Arrays.equals(va1.getLowerBounds(), vb1.getLowerBounds());
            }
        } else if(a instanceof TypeVariable) {
            if(!(b instanceof TypeVariable)) {
                return false;
            } else {
                TypeVariable va = (TypeVariable)a;
                TypeVariable vb = (TypeVariable)b;
                return va.getGenericDeclaration() == vb.getGenericDeclaration() && va.getName().equals(vb.getName());
            }
        } else {
            return false;
        }
    }

    private static int hashCodeOrZero(Object o) {
        return o != null?o.hashCode():0;
    }

    public static String typeToString(Type type) {
        return type instanceof Class?((Class)type).getName():type.toString();
    }

    static Type getGenericSupertype(Type context, Class<?> rawType, Class<?> toResolve) {
        if(toResolve == rawType) {
            return context;
        } else {
            if(toResolve.isInterface()) {
                Class[] rawSupertype = rawType.getInterfaces();
                int i = 0;

                for(int length = rawSupertype.length; i < length; ++i) {
                    if(rawSupertype[i] == toResolve) {
                        return rawType.getGenericInterfaces()[i];
                    }

                    if(toResolve.isAssignableFrom(rawSupertype[i])) {
                        return getGenericSupertype(rawType.getGenericInterfaces()[i], rawSupertype[i], toResolve);
                    }
                }
            }

            if(!rawType.isInterface()) {
                while(rawType != Object.class) {
                    Class var6 = rawType.getSuperclass();
                    if(var6 == toResolve) {
                        return rawType.getGenericSuperclass();
                    }

                    if(toResolve.isAssignableFrom(var6)) {
                        return getGenericSupertype(rawType.getGenericSuperclass(), var6, toResolve);
                    }

                    rawType = var6;
                }
            }

            return toResolve;
        }
    }

    static Type getSupertype(Type context, Class<?> contextRawType, Class<?> supertype) {
        Preconditions.checkArgument(supertype.isAssignableFrom(contextRawType));
        return resolve(context, contextRawType, getGenericSupertype(context, contextRawType, supertype));
    }

    public static Type getArrayComponentType(Type array) {
        return (Type)(array instanceof GenericArrayType?((GenericArrayType)array).getGenericComponentType():((Class)array).getComponentType());
    }

    public static Type getCollectionElementType(Type context, Class<?> contextRawType) {
        Type collectionType = getSupertype(context, contextRawType, Collection.class);
        if(collectionType instanceof WildcardType) {
            collectionType = ((WildcardType)collectionType).getUpperBounds()[0];
        }

        return (Type)(collectionType instanceof ParameterizedType?((ParameterizedType)collectionType).getActualTypeArguments()[0]:Object.class);
    }

    public static Type[] getMapKeyAndValueTypes(Type context, Class<?> contextRawType) {
        if(context == Properties.class) {
            return new Type[]{String.class, String.class};
        } else {
            Type mapType = getSupertype(context, contextRawType, Map.class);
            if(mapType instanceof ParameterizedType) {
                ParameterizedType mapParameterizedType = (ParameterizedType)mapType;
                return mapParameterizedType.getActualTypeArguments();
            } else {
                return new Type[]{Object.class, Object.class};
            }
        }
    }

    public static Type resolve(Type context, Class<?> contextRawType, Type toResolve) {
        while(true) {
            if(toResolve instanceof TypeVariable) {
                TypeVariable var14 = (TypeVariable)toResolve;
                toResolve = resolveTypeVariable(context, contextRawType, var14);
                if(toResolve != var14) {
                    continue;
                }

                return toResolve;
            }

            Type var17;
            if(toResolve instanceof Class && ((Class)toResolve).isArray()) {
                Class var13 = (Class)toResolve;
                Class var16 = var13.getComponentType();
                var17 = resolve(context, contextRawType, var16);
                return (Type)(var16 == var17?var13:arrayOf(var17));
            }

            Type var15;
            if(toResolve instanceof GenericArrayType) {
                GenericArrayType var12 = (GenericArrayType)toResolve;
                var15 = var12.getGenericComponentType();
                var17 = resolve(context, contextRawType, var15);
                return var15 == var17?var12:arrayOf(var17);
            }

            if(toResolve instanceof ParameterizedType) {
                ParameterizedType var11 = (ParameterizedType)toResolve;
                var15 = var11.getOwnerType();
                var17 = resolve(context, contextRawType, var15);
                boolean var18 = var17 != var15;
                Type[] args = var11.getActualTypeArguments();
                int t = 0;

                for(int length = args.length; t < length; ++t) {
                    Type resolvedTypeArgument = resolve(context, contextRawType, args[t]);
                    if(resolvedTypeArgument != args[t]) {
                        if(!var18) {
                            args = (Type[])args.clone();
                            var18 = true;
                        }

                        args[t] = resolvedTypeArgument;
                    }
                }

                return var18?newParameterizedTypeWithOwner(var17, var11.getRawType(), args):var11;
            }

            if(toResolve instanceof WildcardType) {
                WildcardType original = (WildcardType)toResolve;
                Type[] originalLowerBound = original.getLowerBounds();
                Type[] originalUpperBound = original.getUpperBounds();
                Type upperBound;
                if(originalLowerBound.length == 1) {
                    upperBound = resolve(context, contextRawType, originalLowerBound[0]);
                    if(upperBound != originalLowerBound[0]) {
                        return supertypeOf(upperBound);
                    }
                } else if(originalUpperBound.length == 1) {
                    upperBound = resolve(context, contextRawType, originalUpperBound[0]);
                    if(upperBound != originalUpperBound[0]) {
                        return subtypeOf(upperBound);
                    }
                }

                return original;
            }

            return toResolve;
        }
    }

    static Type resolveTypeVariable(Type context, Class<?> contextRawType, TypeVariable<?> unknown) {
        Class declaredByRaw = declaringClassOf(unknown);
        if(declaredByRaw == null) {
            return unknown;
        } else {
            Type declaredBy = getGenericSupertype(context, contextRawType, declaredByRaw);
            if(declaredBy instanceof ParameterizedType) {
                int index = indexOf(declaredByRaw.getTypeParameters(), unknown);
                return ((ParameterizedType)declaredBy).getActualTypeArguments()[index];
            } else {
                return unknown;
            }
        }
    }

    private static int indexOf(Object[] array, Object toFind) {
        for(int i = 0; i < array.length; ++i) {
            if(toFind.equals(array[i])) {
                return i;
            }
        }

        throw new NoSuchElementException();
    }

    private static Class<?> declaringClassOf(TypeVariable<?> typeVariable) {
        GenericDeclaration genericDeclaration = typeVariable.getGenericDeclaration();
        return genericDeclaration instanceof Class?(Class)genericDeclaration:null;
    }

    private static void checkNotPrimitive(Type type) {
        Preconditions.checkArgument(!(type instanceof Class) || !((Class)type).isPrimitive());
    }

    private static final class WildcardTypeImpl implements WildcardType, Serializable {
        private final Type upperBound;
        private final Type lowerBound;
        private static final long serialVersionUID = 0L;

        public WildcardTypeImpl(Type[] upperBounds, Type[] lowerBounds) {
            Preconditions.checkArgument(lowerBounds.length <= 1);
            Preconditions.checkArgument(upperBounds.length == 1);
            if(lowerBounds.length == 1) {
                Preconditions.checkNotNull(lowerBounds[0]);
                Types.checkNotPrimitive(lowerBounds[0]);
                Preconditions.checkArgument(upperBounds[0] == Object.class);
                this.lowerBound = Types.canonicalize(lowerBounds[0]);
                this.upperBound = Object.class;
            } else {
                Preconditions.checkNotNull(upperBounds[0]);
                Types.checkNotPrimitive(upperBounds[0]);
                this.lowerBound = null;
                this.upperBound = Types.canonicalize(upperBounds[0]);
            }

        }

        public Type[] getUpperBounds() {
            return new Type[]{this.upperBound};
        }

        public Type[] getLowerBounds() {
            return this.lowerBound != null?new Type[]{this.lowerBound}:Types.EMPTY_TYPE_ARRAY;
        }

        public boolean equals(Object other) {
            return other instanceof WildcardType && Types.equals(this, (WildcardType)other);
        }

        public int hashCode() {
            return (this.lowerBound != null?31 + this.lowerBound.hashCode():1) ^ 31 + this.upperBound.hashCode();
        }

        public String toString() {
            return this.lowerBound != null?"? super " + Types.typeToString(this.lowerBound):(this.upperBound == Object.class?"?":"? extends " + Types.typeToString(this.upperBound));
        }
    }

    private static final class GenericArrayTypeImpl implements GenericArrayType, Serializable {
        private final Type componentType;
        private static final long serialVersionUID = 0L;

        public GenericArrayTypeImpl(Type componentType) {
            this.componentType = Types.canonicalize(componentType);
        }

        public Type getGenericComponentType() {
            return this.componentType;
        }

        public boolean equals(Object o) {
            return o instanceof GenericArrayType && Types.equals(this, (GenericArrayType)o);
        }

        public int hashCode() {
            return this.componentType.hashCode();
        }

        public String toString() {
            return Types.typeToString(this.componentType) + "[]";
        }
    }

    private static final class ParameterizedTypeImpl implements ParameterizedType, Serializable {
        private final Type ownerType;
        private final Type rawType;
        private final Type[] typeArguments;
        private static final long serialVersionUID = 0L;

        public ParameterizedTypeImpl(Type ownerType, Type rawType, Type... typeArguments) {
            if(rawType instanceof Class) {
                Class t = (Class)rawType;
                Preconditions.checkArgument(ownerType != null || t.getEnclosingClass() == null);
                Preconditions.checkArgument(ownerType == null || t.getEnclosingClass() != null);
            }

            this.ownerType = ownerType == null?null:Types.canonicalize(ownerType);
            this.rawType = Types.canonicalize(rawType);
            this.typeArguments = (Type[])typeArguments.clone();

            for(int var5 = 0; var5 < this.typeArguments.length; ++var5) {
                Preconditions.checkNotNull(this.typeArguments[var5]);
                Types.checkNotPrimitive(this.typeArguments[var5]);
                this.typeArguments[var5] = Types.canonicalize(this.typeArguments[var5]);
            }

        }

        public Type[] getActualTypeArguments() {
            return (Type[])this.typeArguments.clone();
        }

        public Type getRawType() {
            return this.rawType;
        }

        public Type getOwnerType() {
            return this.ownerType;
        }

        public boolean equals(Object other) {
            return other instanceof ParameterizedType && Types.equals(this, (ParameterizedType)other);
        }

        public int hashCode() {
            return Arrays.hashCode(this.typeArguments) ^ this.rawType.hashCode() ^ Types.hashCodeOrZero(this.ownerType);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(30 * (this.typeArguments.length + 1));
            stringBuilder.append(Types.typeToString(this.rawType));
            if(this.typeArguments.length == 0) {
                return stringBuilder.toString();
            } else {
                stringBuilder.append("<").append(Types.typeToString(this.typeArguments[0]));

                for(int i = 1; i < this.typeArguments.length; ++i) {
                    stringBuilder.append(", ").append(Types.typeToString(this.typeArguments[i]));
                }

                return stringBuilder.append(">").toString();
            }
        }
    }



 }
