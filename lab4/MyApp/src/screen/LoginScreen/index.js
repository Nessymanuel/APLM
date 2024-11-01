import React, { useState } from 'react';
import { View, TextInput, Button, Alert } from 'react-native';
import { apiService } from '../../api/apiService';
import styles from './styles'; 

export function LoginScreen({ onLoginSuccess }) {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleLogin = async () => {
        try {
            const message = await apiService.login(username, password);
            Alert.alert('Success', message);
            onLoginSuccess();
        } catch (error) {
            Alert.alert('Error', error );
        }
    };

    return (
        <View style={styles.container}>
            <TextInput
                placeholder="Username"
                value={username}
                onChangeText={setUsername}
                style={styles.input}
            />
            <TextInput
                placeholder="Password"
                value={password}
                onChangeText={setPassword}
                secureTextEntry
                style={styles.input}
            />
            <Button title="Login" onPress={handleLogin} />
            <Button title="Register" onPress={() => onLoginSuccess('register')} />
        </View>
    );
}
