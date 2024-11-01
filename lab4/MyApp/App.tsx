// App.js
import React, { useState } from 'react';
import { SafeAreaView } from 'react-native';
import { LoginScreen } from './src/screen/LoginScreen';
import { RegisterScreen } from './src/screen/RegisterScreen';
import { UserListScreen } from './src/screen/UserListScreen';

const App = () => {
    const [screen, setScreen] = useState('register'); // Inicializa com a tela de login

    const renderScreen = () => {
        switch (screen) {
            case 'login':
                return <LoginScreen onLoginSuccess={() => setScreen('users')} />;
            case 'register':
                return <RegisterScreen />;
            case 'users':
                return <UserListScreen />;
            
        }
    };

    return (
        <SafeAreaView style={{ flex: 1 }}>
            {renderScreen()}
        </SafeAreaView>
    );
};

export default App;
