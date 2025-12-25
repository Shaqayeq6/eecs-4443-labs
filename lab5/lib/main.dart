import 'package:flutter/material.dart';
import 'screens/list_screen.dart';

void main() {
  runApp(const Lab5App());
}

class Lab5App extends StatelessWidget {
  const Lab5App({super.key});

  @override
  Widget build(BuildContext context) {
    const bg = Color(0xFFDFF2EC);      // green-ish mint background
    const accent = Color(0xFF2FBF9B);  // accent green

    return MaterialApp(
      title: "Lab 5",
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        useMaterial3: true,
        scaffoldBackgroundColor: bg,
        colorScheme: ColorScheme.fromSeed(seedColor: accent),
        appBarTheme: const AppBarTheme(
          centerTitle: true,
          backgroundColor: Colors.transparent,
          elevation: 0,
        ),
      ),
      home: const ListScreen(),
    );
  }
}
