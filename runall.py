#!/usr/bin/env python3

import subprocess
import json
import os
import shutil

TIMEOUT = 10 * 60

if os.path.exists('log'):
    shutil.rmtree('log')
os.mkdir('log')

cmd_prefix = ['java', '-jar', 'target\codisexp-1.0-jar-with-dependencies.jar']

configs = json.loads(subprocess.check_output(cmd_prefix + ['-l']).decode("utf-8"))

for algorithm in ['CODIS(3)', 'CODIS-NOCL(3)', 'CEGIS+CBS']:
    for subject in configs['subjects']:
        for tests in subject['tests']:
            cmd_suffix = ['-s', subject['name'], '-t', tests, '-a', algorithm]
            proc = subprocess.Popen(cmd_prefix + cmd_suffix)
            code = proc.wait(timeout=TIMEOUT)
            if os.path.isfile('experiments.log'):
                logfile = 'log/{}-{}-{}.log'.format(algorithm, subject['name'], tests)
                shutil.move('experiments.log', logfile)

