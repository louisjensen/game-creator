package conditions;

public enum ConditionType {

        CollisionCondition (CollisionCondition.class, new Class<?>[]{String.class,String.class}),
        EqualToCondition (EqualToCondition.class, new Class<?>[]{String.class,String.class}),
        GreaterThanCondition (GreaterThanCondition.class, new Class<?>[]{String.class,String.class}),
        LessThanCondition (LessThanCondition.class, new Class<?>[]{String.class,String.class}),
        StringEqualToCondition (StringEqualToCondition.class, new Class<?>[]{String.class,Double.class});


        private final Class<?> className;
        private final Class<?>[] classConstructorTypes;


        ConditionType(Class<?> className, Class<?>[] constructorTypes) {

            this.className = className;
            this.classConstructorTypes = constructorTypes;
        }


        public Class<?>[] getConstructorTypes(){
            return this.classConstructorTypes;
        }

        public Class<?> getClassName(){
            return this.className;
        }


}
